import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {IPayment, IPaymentRequest} from "../IPayment";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  private apiUrl = 'http://localhost:2222/api';

  constructor(private http: HttpClient) {}

  getPayments(): Observable<IPayment[]> {
    return this.http.get<IPayment[]>(`${this.apiUrl}/payments` );
  }

  /**
   * Send the request information to generate the list of payments.
   * @param request
   */
  generatePayments(request: IPaymentRequest): Observable<IPayment[]> {
    return this.http.post<IPayment[]>(`${this.apiUrl}/payments/calculate`, request, httpOptions);
  }
}
