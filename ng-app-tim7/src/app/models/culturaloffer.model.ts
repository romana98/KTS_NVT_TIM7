export class CulturalofferModel {
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
    public pictures: string []
  ) {}
}
