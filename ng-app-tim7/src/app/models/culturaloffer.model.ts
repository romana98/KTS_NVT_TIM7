import {Mappable} from './mappable.interface';

export class CulturalofferModel implements Mappable{
  constructor(
    public id: number,
    public name: string,
    public description: string,
    public startDate: Date,
    public endDate: number,
    public subcategory: number,
    public location: number,
    public subcategoryName: string,
    public categoryName: string,
    public latitude: number,
    public longitude: number,
    public pictures: string []
  ) {}
}