import { FormActionConfig } from "../../../../shared/models/form/form-action-config";
import { FormConfig } from "../../../../shared/models/form/form-config";
import { FormFieldConfig } from "../../../../shared/models/form/form-field-config";

export function buildUserFormConfig(
  mode: 'create' | 'edit' | 'view'
): FormConfig {
  return {
    title:
      mode === 'create'
        ? 'Create User'
        : mode === 'edit'
        ? 'Edit User'
        : 'User Details',

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
      name: 'email',
      label: 'Email',
      type: 'email',
      placeholder: 'Email',
      required: true,
      disabled: mode !== 'create'
    },
    {
      name: 'password',
      label: 'Password',
      type: 'password',
      placeholder: 'Password',
      hidden: mode !== 'create',
      required: mode === 'create'
    },
    {
      name: 'userRole',
      label: 'Role',
      type: 'select',
      disabled: mode !== 'create',
      
      options: [
        { label: 'Admin', value: 'ROLE_ADMIN' },
        { label: 'User', value: 'ROLE_USER' }
      ]
      
    },
     {
      name: 'adminCode',
      label: 'Admin Code',
      type: 'text',
      hidden: true,
      dependsOn: {
        field: 'role',
        value: 'ADMIN',
        action: 'show'
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

