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
      name: 'id',
      label: 'Id',
      type: 'text',
      placeholder: 'Id',
      required: false,
      hidden: mode === 'create'
    },
    {
      name: 'pillar',
      label: 'Pillar Name',
      type: 'select',
      placeholder: 'Pillar',
      required: true,
      optionsConfig :{
        type :'api',
        endpoint: '/api/v1/master-pillars',
        labelKey: 'name',
        valueKey: 'id',
        incomingKey:'name' 
      },
      // disabled: mode !== 'create'
    },
    {
      name: 'description',
      label: 'Description',
      type: 'text',
      placeholder: 'Description',
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

