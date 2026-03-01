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
    route: '/dashboard',
    icon: '🏠',
     children: [
      {
        label: 'Overview',
        route: '/dashboard/overview'
      }
    ]
  },
  {
    label: 'Daily Logs',
     route: '/daily-log',
    icon: '📝',
    children: [
      {
        label: 'Overview',
        route: '/daily-log/overview'
      },
      {
        label: 'History',
        route: '/daily-log/list'
      }
    ]
  },
  {
    label: 'My Pillars',
    route: '/pillars',
    icon: '🧱',
    children: [
      {
        label: 'Overview',
        route: '/pillars/overview'
      },
      {
        label: 'Pillars',
        route: '/pillars/list'
      }
    ]
  },
  {
    label: 'My Activities',
    route: '/activity',
    icon: '⚡',
    children: [
      {
        label: 'Overview',
        route: '/activity/overview'
      },
      {
        label: 'Activities',
        route: '/activity/list'
      }
    ]
  }
];