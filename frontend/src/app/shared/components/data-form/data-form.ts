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
import { FormFieldConfig } from '../../models/form/form-field-config';
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

  @Output() submitForm = new EventEmitter<any>();
  @Output() cancel = new EventEmitter<void>();

  form!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnChanges() {
    if (this.config) {
      this.buildForm();
    }}

  private buildForm() {
    const group: any = {};

    this.config.fields.forEach(field => {
      if (field.hidden) return;

      const validators = [];

      if (field.required) validators.push(Validators.required);
      if (field.minLength) validators.push(Validators.minLength(field.minLength));
      if (field.maxLength) validators.push(Validators.maxLength(field.maxLength));
      if (field.min !== undefined) validators.push(Validators.min(field.min));
      if (field.max !== undefined) validators.push(Validators.max(field.max));
      if (field.pattern) validators.push(Validators.pattern(field.pattern));

      group[field.name] = [
        this.data?.[field.name] ?? field.defaultValue ?? '',
        validators
      ];
    });

    this.form = this.fb.group(group);

    if (this.config.readOnly || this.config.mode === 'view') {
      this.form.disable();
    }
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

  
}
