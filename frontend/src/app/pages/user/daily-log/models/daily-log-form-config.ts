import { FormActionConfig } from "../../../../shared/models/form/form-action-config";
import { FormConfig } from "../../../../shared/models/form/form-config";
import { FormFieldConfig } from "../../../../shared/models/form/form-field-config";

export function buildDailyLogFormConfig(
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
      name: 'activityDailyLogId',
      label: 'Id',
      type: 'text',
      placeholder: 'Id',
      hidden :mode === 'create',
      disabled: mode !== 'create'
    },
    {
      name: 'activityId',
      label: 'Activity',
      type: 'select',
      placeholder: 'Activity',
      required: true,
      optionsConfig :{
        type :'api',
        endpoint: '/api/v1/activity',
        labelKey: 'activityName',
        valueKey: 'activityId',
        incomingKey: 'activityName' 
      },
      disabled: mode !== 'create'
    },
    {
      name: 'actualValue',
      label: 'Value',
      type: 'text',
      placeholder: 'Value',
      // required: true
    },
    {
      name: 'completed',
      label: 'Completed',
      placeholder: 'Completed',
      type: 'select',
      required: true,
      optionsConfig :{
        type :'static',
        options:[
          { label: 'Yes', value: true },
          { label: 'No', value: false }
        ], 
      }
    },
    {
      name: 'notes',
      label: 'Notes',
      type: 'text',
      placeholder: 'Description',
      // required: true,
      // disabled: mode !== 'create'
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

