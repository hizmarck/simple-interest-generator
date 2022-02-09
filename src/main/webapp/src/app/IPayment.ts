// Payment data
export interface IPayment {
  id?: number;
  number: number;
  amount: number;
  pending: number;
  date: string;
}

// Requested data to generate the payments.
export interface IPaymentRequest {
  amount: number;
  terms: number;
  rate: number;
}
