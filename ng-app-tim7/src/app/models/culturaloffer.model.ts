import {Mappable} from './mappable.interface';

export class CulturalofferModel implements Mappable{
  constructor(
    public id: number,
    public name: string,
    public description: string,
    public startDate: number,
    public endDate: number,
    public subcategory: number,
    public location: number,
    public subcategoryName: string,
    public categoryName: string,
    public category: number,
    public latitude: number,
    public longitude: number,
    public pictures: string []
  ) {}

  public getDefault(): CulturalofferModel{
    return {
      id: -1,
      name: '',
      description: '',
      startDate: -1,
      endDate: -1,
      subcategory: -1,
      location: -1,
      subcategoryName: '',
      categoryName: '',
      category: -1,
      latitude: -1,
      longitude: -1,
      pictures: []
    } as CulturalofferModel;
  }
}
