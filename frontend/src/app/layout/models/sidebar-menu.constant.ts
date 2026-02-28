export interface SidebarItem {
  label: string;
  route?: string;              // optional (if parent-only section)
  icon?: string;               // optional icon
  children?: SidebarItem[];    // recursive support
}

/* ADMIN MENU */
export const ADMIN_SIDEBAR_MENU: SidebarItem[] = [
  {
    label: 'Dashboard',
    route: '/admin/dashboard',
    icon: '🏠'
  },
  {
    label: 'Users',
    icon: '👥',
    children: [
      {
        label: 'All Users',
        route: '/admin/users'
      },
      {
        label: 'Add User',
        route: '/admin/users/create'
      }
    ]
  },
  {
    label: 'Pillars',
    icon: '🧱',
    children: [
      {
        label: 'All Pillars',
        route: '/admin/pillars'
      },
      {
        label: 'Create Pillar',
        route: '/admin/pillars/create'
      }
    ]
  },
  {
    label: 'Activity',
    route: '/admin/activity',
    icon: '📊'
  }
];

/* USER MENU */
export const USER_SIDEBAR_MENU: SidebarItem[] = [
  {
    label: 'Dashboard',
    route: '/user/dashboard',
    icon: '🏠',
     children: [
      {
        label: 'Overview',
        route: '/user/dashboard/overview'
      }
    ]
  },
  {
    label: 'Daily Logs',
     route: '/user/daily-log',
    icon: '📝',
    children: [
      {
        label: 'Overview',
        route: '/user/daily-log/overview'
      },
      {
        label: 'History',
        route: '/user/daily-log/list'
      }
    ]
  },
  {
    label: 'My Pillars',
    route: '/user/pillars',
    icon: '🧱',
    children: [
      {
        label: 'Overview',
        route: '/user/pillars/overview'
      }
    ]
  },
  {
    label: 'My Activities',
    route: '/user/activity',
    icon: '⚡',
    children: [
      {
        label: 'Overview',
        route: '/user/activity/overview'
      }
    ]
  }
];