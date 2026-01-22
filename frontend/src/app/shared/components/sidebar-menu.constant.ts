export interface SidebarItem {
  label: string;
  icon?: string;
  route: string;
}

/* ADMIN MENU */
export const ADMIN_SIDEBAR_MENU: SidebarItem[] = [
  {
    label: 'Dashboard',
    route: '/admin/dashboard'
  },
  {
    label: 'Users',
    route: '/admin/users'
  },
  {
    label: 'Pillars',
    route: '/admin/pillars'
  },
  {
    label: 'Activity',
    route: '/admin/activity'
  }
];

/* USER MENU */
export const USER_SIDEBAR_MENU: SidebarItem[] = [
  {
    label: 'Dashboard',
    route: '/user/dashboard'
  },
  {
    label: 'My Pillars',
    route: '/user/pillars'
  },
  {
    label: 'Activity',
    route: '/user/activity'
  }
];
