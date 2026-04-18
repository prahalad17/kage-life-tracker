import { Component, Input } from '@angular/core';
import { WidgetConfig } from '../../../../models/dashboard/widget-config';
import { MetricWidgetData } from '../../../../models/dashboard/widgets/metric-widget-data';

@Component({
  selector: 'app-metric-widget',
  imports: [],
  templateUrl: './metric-widget.html',
  styleUrl: './metric-widget.scss',
})
export class MetricWidget {

   @Input({ required: true }) config!: WidgetConfig;
  @Input({ required: true }) data!: MetricWidgetData;

}
