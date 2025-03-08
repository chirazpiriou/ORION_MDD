export class Commentaire {
  constructor(
    public userName: string,
    public contenu: string,
    public createdAt: Date,
    public userId: number,
    public id?: number,
    public articleId?: number
    
  ) {}
}
