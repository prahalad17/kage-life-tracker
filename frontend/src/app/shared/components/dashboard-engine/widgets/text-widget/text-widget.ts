import { Component, Input } from '@angular/core';
import { WidgetConfig } from '../../../../models/dashboard/widget-config';
import { TextWidgetData } from '../../../../models/dashboard/widgets/text-widget-data';

@Component({
  selector: 'app-text-widget',
  imports: [],
  templateUrl: './text-widget.html',
  styleUrl: './text-widget.scss',
})
export class TextWidget {

  @Input({ required: true }) config!: WidgetConfig;
  @Input({ required: true }) data!: TextWidgetData;

}
