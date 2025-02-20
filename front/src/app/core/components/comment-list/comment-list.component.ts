import { Component, Input, OnInit } from '@angular/core';
import { Commentaire } from '../../models/commentaire.model';

@Component({
  selector: 'app-comment-list',
  templateUrl: './comment-list.component.html',
  styleUrls: ['./comment-list.component.scss']
})
export class CommentListComponent implements OnInit {
  @Input() commentaire!: Commentaire;
  constructor() { }

  ngOnInit(): void {
  }

}
