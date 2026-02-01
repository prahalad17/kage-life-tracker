import { FormActionConfig } from "../../../../shared/models/form/form-action-config";
import { FormConfig } from "../../../../shared/models/form/form-config";
import { FormFieldConfig } from "../../../../shared/models/form/form-field-config";

export function buildActivityFormConfig(
  mode: 'create' | 'edit' | 'view'
): FormConfig {
  return {
    title:
      mode === 'create'
        ? 'Create Pillar'
        : mode === 'edit'
        ? 'Edit Pillar'
        : 'Pillar Details',

    mode,
    readOnly: mode === 'view',

    layout: 'grid',
    gridColumns: 2,

    fields: getUserFields(mode),
    actions: getUserActions(mode)
  };
}
function getUserFields(mode: 'create' | 'edit' | 'view'): FormFieldConfig[] {
  return [
    {
      name: 'name',
      label: 'Name',
      type: 'text',
      placeholder: 'Name',
      required: true
    },
    {
      name: 'description',
      label: 'Description',
      type: 'text',
      placeholder: 'Description',
      required: true,
      // disabled: mode !== 'create'
    },
    {
      name: 'pillar',
      label: 'Pillar',
      type: 'select',
      placeholder: 'Pillar',
      required: true,
      optionsConfig :{
        type :'api',
        endpoint: '/api/v1/master-pillars',
        labelKey: 'name',
        valueKey: 'code'
      },
      disabled: mode !== 'create'
    },
    {
      name: 'activityNature',
      label: 'Activity Nature',
      type: 'select',
      placeholder: 'Activity Nature',
      required: true,
      optionsConfig :{
        type :'static',
        options:[
          { label: 'Positive', value: 'POSITIVE' },
          { label: 'Negative', value: 'NEGATIVE' }
        ]
      }
    },
    {
      name: 'defaultTrackingType',
      label: 'Tracking Type',
      type: 'select',
      placeholder: 'Tracking Type',
      required: true,
      optionsConfig :{
        type :'static',
        options:[
          { label: 'Task Completion', value: 'BOOLEAN' },
          { label: 'Task Count', value: 'COUNT' },
          { label: 'Task Duration', value: 'DURATION' }
        ]
      }
    }

  ];
}

function getUserActions(mode: 'create' | 'edit' | 'view'): FormActionConfig[] {
  if (mode === 'view') {
    return [  
      {
        label: 'Close',
        type: 'button',
        action: 'cancel'
      }
    ];
  }

  return [
    {
      label: 'Cancel',
      type: 'button',
      action: 'cancel'
    },
    {
      label: mode === 'create' ? 'Create' : 'Update',
      type: 'submit',
      action: 'submit'
    }
  ];
}

