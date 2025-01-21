import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent  {

  loginForm = new FormGroup({
    emailOrUsername: new FormControl(''),
    password: new FormControl(''),
  });
  onSubmit() {
    console.log(this.loginForm.value);
  }
}
