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
import { FormActionConfig } from '../../models/form/form-action-config';
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
 @Input() message: string = '';
  

  @Output() submitForm = new EventEmitter<any>();
  @Output() cancel = new EventEmitter<void>();

  form!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnChanges() {
  if (this.config && !this.form) {
    this.buildForm();
  }

  if (this.form && this.data) {
    this.form.patchValue(this.data);
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
