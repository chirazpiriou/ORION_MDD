export class Comment {
    constructor(
      public user_str: string,
      public contenu: string,
      public createdAt: Date,
      public id?: number,
      public article_id?: number
    ) {}
  }