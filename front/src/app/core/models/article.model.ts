import { Comment} from "./comment.model";

export class Article {
  constructor(
    public theme: string, 
    public titre: string,
    public auteur: string,
    public contenu: string,
    public commentaires: Comment[],
    public createdAt: Date,
    public id?: number, 
  ) {}
}