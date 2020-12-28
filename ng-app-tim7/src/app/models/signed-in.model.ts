export class SignedInModel {
  constructor(
    public username: string,
    public id: number,
    private accessToken: string,
    private  role: string
  ) {}
}
