import { Component, EventEmitter, Input, Output } from '@angular/core';
import { WidgetConfig } from '../../../../models/dashboard/widget-config';
import { InteractiveListWidgetData } from '../../../../models/dashboard/widgets/interactive-list-widget-data';
import { WidgetEvent } from '../../../../models/dashboard/widget-event';

@Component({
  selector: 'app-interactive-list-widget',
  imports: [],
  templateUrl: './interactive-list-widget.html',
  styleUrl: './interactive-list-widget.scss',
})
export class InteractiveListWidget {

  @Input({ required: true }) config!: WidgetConfig;
  @Input({ required: true }) data!: InteractiveListWidgetData;

  @Output() action = new EventEmitter<WidgetEvent>();

  onToggle(itemId: string) {
    this.action.emit({
      widgetId: this.config.id,
      actionType: 'TOGGLE',
      payload: { itemId }
    });
  }

}
