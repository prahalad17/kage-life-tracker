import { DashboardConfig } from "../../../../../shared/models/dashboard/dashboard-config";

export const dashboardConfig: DashboardConfig = {
  id: 'home',

  layout: {
    type: 'grid',
    columns: 2
  },

  widgets: [
    {
      id: 'tasks',
      type: 'interactive-list',
      title: "Today's Tasks",
      dataKey: 'tasks'
    },
    {
      id: 'actions',
      type: 'list',
      title: 'Completed Actions',
      dataKey: 'actions'
    },
    {
      id: 'sleep',
      type: 'metric',
      title: 'Sleep',
      dataKey: 'sleepData'
    },
    {
      id: 'note',
      type: 'text',
      title: 'Daily Note',
      dataKey: 'note'
    }
  ]
};