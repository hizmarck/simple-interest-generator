import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {IPaymentRequest} from "../../IPayment";
import {UiService} from "../../services/ui.service";
import {Subscription} from "rxjs";
import {NgModel} from "@angular/forms";

@Component({
  selector: 'app-generate-payment',
  templateUrl: './generate-payment.component.html',
  styleUrls: ['./generate-payment.component.css']
})
export class GeneratePaymentComponent implements OnInit {

  @Output() onGeneratePayments = new EventEmitter<IPaymentRequest>();

  showAdd = false;
  subscription: Subscription;
  amount = 1000;
  rate = 10.0;
  terms = 4;

  constructor(private uiService: UiService) {
    this.subscription = this.uiService.onShowCalculatePayment().subscribe((show) => this.showAdd = show);
  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

  onSubmit(): void {
    // remember the validations

    const newRequest: IPaymentRequest = {
      amount: this.amount,
      rate: this.rate,
      terms: this.terms
    };

    this.onGeneratePayments.emit(newRequest);

    this.amount = 0;
    this.terms = 4;
    this.rate = 10;
  }

  invalid(element: NgModel): boolean | null {
    return element.invalid && (element.dirty || element.touched);
  }

}
