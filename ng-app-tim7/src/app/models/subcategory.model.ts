export class SubcategoryModel {
  constructor(
    public id: number,
    public name: string,
    public categoryId: number,
    public categoryName: string
  ) {}
}
