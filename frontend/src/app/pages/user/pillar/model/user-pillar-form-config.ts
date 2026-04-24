import { FormActionConfig } from "../../../../shared/models/form/form-action-config";
import { FormConfig } from "../../../../shared/models/form/form-config";
import { FormFieldConfig } from "../../../../shared/models/form/form-field-config";

export function buildUserPillarFormConfig(
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
      name: 'pillarId',
      label: 'Id',
      type: 'text',
      placeholder: 'Id',
      required: false,
      hidden: mode === 'create',
      disabled: mode !== 'create'
    },

    // {
    //   name: 'pillarTemplateId', 
    //   label: 'Pillar Template',
    //   type: 'select',
    //   placeholder: 'Pillar',
    //   required: false,
    //   optionsConfig :{
    //     type :'api',
    //     endpoint: '/api/v1/pillar-template',
    //     labelKey: 'pillarName',
    //     valueKey: 'id',
    //     incomingKey:'pillarName' 
    //   }
    // },
    {
      name: 'pillarName', 
      label: 'Pillar Name',
      type: 'text',
      placeholder: 'Pillar',
      required: true
      
      // disabled: mode !== 'create'
    },
    {
      name: 'pillarDescription',
      label: 'Description',
      type: 'text',
      placeholder: 'Description',
      required: false,
      // disabled: mode !== 'create'
    },
    {
      name: 'priorityWeight',
      label: 'Priority',
      type: 'number',
      placeholder: 'Priority',
      required: false,
      // disabled: mode !== 'create'
    },
    {
      name: 'orderIndex',
      label: 'Order ',
      type: 'number',
      placeholder: 'Order',
      required: false,
      // disabled: mode !== 'create'
    },
    {
      name: 'pillarColor',
      label: 'Pillar Color',
      type: 'color',
      placeholder: 'Color',
      required: false,
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