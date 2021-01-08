export class NewsletterModel {
  constructor(
    public id: number,
    public name: string,
    public description: string,
    public publishedDate: Date,
    public culturalOfferId: number,
    public picture: string
  ) {}
}

