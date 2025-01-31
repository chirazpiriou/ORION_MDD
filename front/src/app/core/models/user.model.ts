export class User {
    constructor(
      public email: string,
      public username: string,
      public password: string,
      public createdAt: Date,
      public id?: number,
    ) {}
  
    toString():string{
      return this.username;
    }
  }