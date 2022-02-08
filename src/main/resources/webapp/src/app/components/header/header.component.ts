import {Component, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {UiService} from "../../services/ui.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  title = 'Simple Interest Generator';
  showAdd = false;
  subscription: Subscription;

  constructor(private uiService: UiService) {
    this.subscription = uiService.onShowCalculatePayment().subscribe((toggle) => this.showAdd = toggle);
  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

  toggleClick(): void {
    this.uiService.toggleShowCalculatePayments();
  }

}
