import { FormConfig } from "../../../../shared/models/form/form-config";

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
function getUserFields(mode: string) {
  return [
    {
      name: 'name',
      label: 'Name',
      type: 'text',
      required: true
    },
    {
      name: 'email',
      label: 'Email',
      type: 'email',
      required: true,
      disabled: mode !== 'create'
    },
    {
      name: 'password',
      label: 'Password',
      type: 'password',
      hidden: mode !== 'create',
      required: mode === 'create'
    },
    {
      name: 'role',
      label: 'Role',
      type: 'select',
      options: [
        { label: 'Admin', value: 'ADMIN' },
        { label: 'User', value: 'USER' }
      ]
    }
  ];
}
function getUserActions(mode: string) {
  if (mode === 'view') {
    return [
      { label: 'Close', type: 'button', action: 'cancel' }
    ];
  }

  return [
    { label: 'Cancel', type: 'button', action: 'cancel' },
    {
      label: mode === 'create' ? 'Create' : 'Update',
      type: 'submit'
    }
  ];
}
