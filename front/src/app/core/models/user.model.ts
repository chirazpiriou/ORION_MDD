export class User {
  constructor(
    public email: string,
    public name: string,
    public password: string,
    public createdAt: Date,
    public id?: number
  ) {}

  toString(): string {
    return this.name;
  }
}
