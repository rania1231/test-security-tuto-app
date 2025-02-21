import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-products',
  standalone: false,
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent {
  public products: any;
    constructor(private http:HttpClient){

    }
    ngOnInit(){
      this.http.get("http://localhost:8082/api/products")
      .subscribe({
        next:data => {this.products=data;

          console.log(data);
        },
        error:err=>{
          console.log(err)}
      
      })
    }
}
