import {NgModule} from '@angular/core';
import {TableComponent} from './table/table.component';
import {PaginationComponent} from './pagination/pagination.component';
import {GoogleMapComponent} from './google-map/google-map.component';
import {CarouselComponent} from './carousel/carousel.component';
import {AgmCoreModule} from '@agm/core';
import {MatCarouselModule} from '@ngmodule/material-carousel';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatTableModule} from '@angular/material/table';
import {CommonModule} from '@angular/common';
import {MaterialModule} from './material.module';

@NgModule({
  declarations: [TableComponent, PaginationComponent, GoogleMapComponent, CarouselComponent],
  imports: [
    MatCarouselModule.forRoot(),
    MatPaginatorModule,
    MatTableModule,
    MaterialModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyA299mClrC7nDZzy92CQ4X47y7FmaBKMj4'
    })
  ],
  exports: [
    TableComponent, PaginationComponent, GoogleMapComponent, CarouselComponent
  ],
  providers: []
})
export class SharedModule { }
