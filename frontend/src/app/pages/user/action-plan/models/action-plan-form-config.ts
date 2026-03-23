import { FormActionConfig } from "../../../../shared/models/form/form-action-config";
import { FormConfig } from "../../../../shared/models/form/form-config";
import { FormFieldConfig } from "../../../../shared/models/form/form-field-config";

export function buildActionPlanFormConfig(
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
      name: 'actionPlanId',
      label: 'Id',
      type: 'text',
      placeholder: 'Id',
      hidden :mode === 'create',
      disabled: mode !== 'create'
    },

    {
      name: 'actionPlanDate',
      label: 'Action Plan Date',
      type: 'date',
      placeholder: 'Value',
      required: true
    },
    {
      name: 'actionPlanName',
      label: 'Action Plan Name',
      type: 'text',
      placeholder: 'Name',
      required: true
    },
    {
      name: 'activityId',
      label: 'Activity',
      type: 'select',
      placeholder: 'Activity',
      // required: true,
      optionsConfig :{
        type :'api',
        endpoint: '/api/v1/activity',
        labelKey: 'activityName',
        valueKey: 'activityId',
        incomingKey: 'activityName' 
      },
       dependsOn: {
    field: 'activityId',
    condition: (v) => !!v,
    actions: [
      {
        type: 'disable',
        targets: ['pillarName', 'trackingType', 'nature']
      },
      {
        type: 'patchFromOption',
        mapping: {
          pillarName: 'pillarName',
          trackingType: 'trackingType',
          nature: 'nature'
        }
      }
    ]
  },
      disabled: mode !== 'create'
    },

    {
      name: 'pillarName',
      label: 'Pillar',
      type: 'select',
      placeholder: 'Pillar',
      // required: true,
      optionsConfig :{
        type :'api',
        endpoint: '/api/v1/pillar',
        labelKey: 'pillarName',
        valueKey: 'id',
        incomingKey: 'pillarName' 
      },
       dependsOn: {
    field: 'pillarName',
    condition: (v) => !!v,
    actions: [
      {
        type: 'disable',
        targets: ['activityId']
      }
    ]
  }
      // disabled: mode !== 'create'
    },

    {
      name: 'actionPlanNature',
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
      name: 'actionPlanTrackingType',
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
      name: 'actionPlanStatus',
      label: 'Status',
      placeholder: 'Status',
      type: 'select',
      required: true,
      optionsConfig :{
        type :'static',
        options:[
          { label: 'SCHEDULED', value: 'SCHEDULED' },
          { label: 'IN_PROGRESS', value: 'IN_PROGRESS' },
           { label: 'PARTIALLY_COMPLETED', value: 'PARTIALLY_COMPLETED' },
            { label: 'COMPLETED', value: 'COMPLETED' },
          { label: 'MISSED', value: 'MISSED' }
        ], 
      }
    },
    {
      name: 'actionPlanNotes',
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

