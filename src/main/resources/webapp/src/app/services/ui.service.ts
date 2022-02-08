import {Injectable} from '@angular/core';
import {Observable, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UiService {

  private showCalculatePayments = false;
  private subject = new Subject<any>();

  constructor() {}

  toggleShowCalculatePayments() {
    this.showCalculatePayments = !this.showCalculatePayments;
    this.subject.next(this.showCalculatePayments);
  }

  onShowCalculatePayment(): Observable<any> {
    return this.subject.asObservable();
  }

}
