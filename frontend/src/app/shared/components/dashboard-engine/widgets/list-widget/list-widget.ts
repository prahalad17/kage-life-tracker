import { Component, Input } from '@angular/core';
import { WidgetConfig } from '../../../../models/dashboard/widget-config';
import { ListWidgetData } from '../../../../models/dashboard/widgets/list-widget-data';

@Component({
  selector: 'app-list-widget',
  imports: [],
  templateUrl: './list-widget.html',
  styleUrl: './list-widget.scss',
})
export class ListWidget {

    @Input({ required: true }) config!: WidgetConfig;
  @Input({ required: true }) data!: ListWidgetData;

}
