import {Component, Input, OnInit} from '@angular/core';
import {IPayment} from "../../IPayment";

@Component({
  selector: 'app-payment-item',
  templateUrl: './payment-item.component.html',
  styleUrls: ['./payment-item.component.css']
})
export class PaymentItemComponent implements OnInit {

  @Input()
  payment!: IPayment;

  constructor() { }

  ngOnInit(): void {}

}
