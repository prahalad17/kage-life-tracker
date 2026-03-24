import { FormActionConfig } from "../../../../shared/models/form/form-action-config";
import { FormConfig } from "../../../../shared/models/form/form-config";
import { FormFieldConfig } from "../../../../shared/models/form/form-field-config";

export function buildUserActivityFormConfig(
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
      name: 'activityId',
      label: 'Id',
      type: 'text',
      placeholder: 'Id',
      hidden :mode === 'create',
      disabled: mode !== 'create'
    },
    {
      name: 'activityName',
      label: 'Name',
      type: 'text',
      placeholder: 'Name',
      required: true
    },
    
    {
      name: 'pillarName',
      label: 'Pillar',
      type: 'select',
      placeholder: 'Pillar',
      optionsConfig :{
        type :'api',
        endpoint: '/api/v1/pillar',
        labelKey: 'pillarName',
        valueKey: 'id',
        incomingKey: 'pillarName' 
      }
      // disabled: mode !== 'create'
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
      name: 'activityTrackingType',
      label: 'Tracking Type',
      type: 'select',
      placeholder: 'Tracking Type',
      required: true,
      optionsConfig :{
        type :'static',
        options:[
          { label: 'Task Completion', value: 'BOOLEAN' },
          { label: 'Task Count', value: 'COUNT' },
          { label: 'Task Duration', value: 'DURATION' },
          { label: 'Avoidance', value: 'AVOIDANCE' }
        ]
      }
    },
    {
      name: 'activityDescription',
      label: 'Description',
      type: 'text',
      placeholder: 'Description',
      required: true,
      // disabled: mode !== 'create'
    },
     {
      name: 'activityScheduleType',
      label: 'Schedule Type',
      type: 'select',
      placeholder: 'Schedule Type',
      required: true,
      optionsConfig :{
        type :'static',
        options:[
          { label: 'Daily', value: 'DAILY' },
          { label: 'Weekdays', value: 'WEEKDAYS' },
          { label: 'Weekends', value: 'WEEKENDS' }
        ]
      }
    },
    {
      name: 'activityType',
      label: 'Activity Type',
      type: 'select',
      placeholder: 'Activity Type',
      required: true,
      optionsConfig :{
        type :'static',
        options:[
          { label: 'Daily', value: 'HABIT' },
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

