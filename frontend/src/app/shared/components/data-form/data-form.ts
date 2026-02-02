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
  styleUrl: './data-form.css',
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
          value: item[cfg.valueKey]
          
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
    const target = this.form.get(field.name);

    if (!controller || !target) return;

    controller.valueChanges.subscribe(value => {
      this.applyDependency(dep, value, field);
    });
  });
}

private applyDependency(
  dep: FieldDependency,
  value: any,
  field: FormFieldConfig
) {
  const control = this.form.get(field.name);
  if (!control) return;

  const conditionMet = dep.condition
    ? dep.condition(value)
    : value === dep.value;

  switch (dep.action) {
    case 'show':
      field.hidden = !conditionMet;
      break;

    case 'hide':
      field.hidden = conditionMet;
      break;

    case 'enable':
      conditionMet ? control.enable() : control.disable();
      break;

    case 'disable':
      conditionMet ? control.disable() : control.enable();
      break;
  }
}

}
