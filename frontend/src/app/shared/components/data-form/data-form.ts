import { CommonModule } from '@angular/common';
import { Component, Input, OnInit, Output, EventEmitter, OnChanges } from '@angular/core';
import {
  FormGroup,
  FormControl,
  Validators,
  ReactiveFormsModule,
  FormBuilder
} from '@angular/forms';

import { FormConfig } from '../../models/form/form-config';
import { FieldDependency, FormFieldConfig } from '../../models/form/form-field-config';
import { HttpClient } from '@angular/common/http';
@Component({
  selector: 'app-data-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './data-form.html',
  styleUrl: './data-form.scss',
})
export class DataForm implements OnChanges {

  @Input() config!: FormConfig;
  @Input() data: any;
  private formData: any;
 @Input() message: string = '';
  

  @Output() submitForm = new EventEmitter<any>();
  @Output() cancel = new EventEmitter<void>();

  form!: FormGroup;

  private adapted = false;

  constructor(private fb: FormBuilder ,private http: HttpClient) {}


 dropdownOptions: Record<string,{ label: string; value: any }[] | undefined> = {};

  ngOnChanges() {
  if (this.config && !this.form) {
    this.buildForm();
    this.loadDropdownOptions();
  }

  if (this.form && this.data) {

     this.adapted = false;
    // 🔒 clone ONCE per input change
    this.formData = structuredClone(this.data);

    this.adaptIncomingData(this.formData);
    this.form.patchValue(this.formData);
  }
}
  private buildForm() {
  const group: Record<string, any> = {};

  this.config.fields.forEach(field => {
    if (field.hidden) return;

    group[field.name] = this.fb.control(
     {
      value: field.defaultValue ?? '',
      disabled: field.disabled === true
      },
    this.buildValidators(field)
    );
  });

  this.form = this.fb.group(group);

   this.setupDependencies();

  if (this.config.readOnly || this.config.mode === 'view') {
    this.form.disable();
  }
}

private loadDropdownOptions() {
  this.config.fields.forEach(field => {

    if (field.type !== 'select' || !field.optionsConfig) {
      return;
    }

    // 🟢 STATIC OPTIONS
    if (field.optionsConfig.type === 'static') {
      this.dropdownOptions[field.name] =
        field.optionsConfig.options;
    }

    // 🔵 API OPTIONS
    if (field.optionsConfig.type === 'api') {
      const cfg = field.optionsConfig;

      this.http.get<any[]>(cfg.endpoint).subscribe(res => {
        this.dropdownOptions[field.name] = res.map(item => ({
          
          label: item[cfg.labelKey],
          value: item[cfg.valueKey],
           ...item
        }));
         if (this.formData) {
          this.adaptIncomingData(this.formData);
          this.form?.patchValue(this.formData);
        }
      });
    }

  });
}

private adaptIncomingData(data: any) {

 let didAdapt = false;

  this.config.fields.forEach(field => {
    if (field.type !== 'select') return;
    if (!field.optionsConfig) return;
    if (field.optionsConfig.type !== 'api') return;

    const options = this.dropdownOptions[field.name];
    if (!options || !options.length) return;

    const incomingValue = data[field.name];
    if (incomingValue == null) return;

    const { incomingKey, labelKey } = field.optionsConfig;

    let match;

    if (incomingKey === labelKey) {
      match = options.find(o => o.label === incomingValue);
    } else {
      match = options.find(o => o.value === incomingValue);
    }

    if (match) {
      data[field.name] = match.value;
      didAdapt = true;
    }
  });

  if (didAdapt) {
    this.adapted = true;
  }
}

  private buildValidators(field: FormFieldConfig) {
  const validators = [];

  if (field.required) validators.push(Validators.required);
  if (field.minLength) validators.push(Validators.minLength(field.minLength));
  if (field.maxLength) validators.push(Validators.maxLength(field.maxLength));
  if (field.min !== undefined) validators.push(Validators.min(field.min));
  if (field.max !== undefined) validators.push(Validators.max(field.max));
  if (field.pattern) validators.push(Validators.pattern(field.pattern));

  return validators;
}

   onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.submitForm.emit(this.form.getRawValue());
  }

  onCancel() {
    this.cancel.emit();
  }

  private setupDependencies() {
  this.config.fields.forEach(field => {
    if (!field.dependsOn) return;

    const dep = field.dependsOn;
    const controller = this.form.get(dep.field);

    if (!controller) return;

    controller.valueChanges.subscribe(value => {
      this.applyDependency(dep, value);
    });
  });
}

private applyDependency(
  dep: FieldDependency,
  value: any
) {
  const conditionMet = dep.condition
    ? dep.condition(value)
    : value === dep.value;

  if (!dep.actions) return;

  dep.actions.forEach(action => {

    // ✅ ENABLE / DISABLE / SHOW / HIDE
    if (['enable', 'disable', 'show', 'hide'].includes(action.type)) {
      action.targets?.forEach(targetName => {
        const targetCtrl = this.form.get(targetName);
        const targetField = this.config.fields.find(f => f.name === targetName);

        if (!targetCtrl || !targetField) return;

        switch (action.type) {
          case 'enable':
            conditionMet ? targetCtrl.enable({ emitEvent: false }) : targetCtrl.disable({ emitEvent: false });
            break;

          case 'disable':
            conditionMet ? targetCtrl.disable({ emitEvent: false }) : targetCtrl.enable({ emitEvent: false });
            break;

          case 'show':
            targetField.hidden = !conditionMet;
            break;

          case 'hide':
            targetField.hidden = conditionMet;
            break;
        }
      });
    }

    // ✅ PATCH FROM SELECTED OPTION
    if (action.type === 'patchFromOption' && conditionMet && value) {
      const options = this.dropdownOptions[dep.field];
      const selected = options?.find(o => o.value === value);

      if (!selected) return;

      const patch: any = {};

      Object.entries(action.mapping || {}).forEach(([target, sourceKey]) => {
        patch[target] = (selected as any)[sourceKey];
      });

      this.form.patchValue(patch, { emitEvent: false });
    }

    // ✅ STATIC VALUE SET
    if (action.type === 'setValue' && conditionMet) {
      this.form.patchValue(action.values || {}, { emitEvent: false });
    }

  });
}

onHexChange(controlName: string, value: string) {
  const hexRegex = /^#([0-9A-Fa-f]{6})$/;

  if (hexRegex.test(value)) {
    this.form.get(controlName)?.setValue(value);
  }
}

}
