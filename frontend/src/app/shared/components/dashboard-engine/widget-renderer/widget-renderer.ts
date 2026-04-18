import { Component, EventEmitter, Input, Output } from '@angular/core';
import { WidgetConfig } from '../../../models/dashboard/widget-config';
import { WidgetEvent } from '../../../models/dashboard/widget-event';
import { MetricWidget } from '../widgets/metric-widget/metric-widget';
import { ListWidget } from '../widgets/list-widget/list-widget';
import { InteractiveListWidget } from '../widgets/interactive-list-widget/interactive-list-widget';
import { TextWidget } from '../widgets/text-widget/text-widget';

@Component({
  selector: 'app-widget-renderer',
  imports: [MetricWidget,
    ListWidget,
    InteractiveListWidget,
    TextWidget
  ],
  templateUrl: './widget-renderer.html',
  styleUrl: './widget-renderer.scss',
})
export class WidgetRenderer {

   @Input({ required: true }) config!: WidgetConfig;
  @Input() data: any;

  @Output() action = new EventEmitter<WidgetEvent>();

  onWidgetEvent(event: WidgetEvent) {
    this.action.emit(event);
  }

}
