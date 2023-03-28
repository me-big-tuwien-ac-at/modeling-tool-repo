import {Component, HostListener, OnInit} from '@angular/core';
import {SharedService} from '../services/shared.service';
import {ThemeEnum} from '../dtos/theme';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  public phoneMode: boolean = false;
  public theme: ThemeEnum = 0;

  constructor(
    private sharedService: SharedService
  ) { }

  @HostListener('window:resize', ['$event'])
  // @ts-ignore
  onResize(event): void {
    this.phoneMode = event.target.innerWidth < 1400;
  }

  ngOnInit(): void {
    this.phoneMode = window.innerWidth < 1400;
  }

  public switchTheme(): void {
    this.theme = (this.theme + 1) % 2
    this.sharedService.switchTheme();
  }
}
