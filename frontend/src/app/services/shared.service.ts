import {EventEmitter, Injectable, Output} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {ThemeEnum} from '../dtos/theme';
import {ZoomStatus} from '../dtos/page-interaction-properties';

@Injectable({
  providedIn: 'root'
})
export class SharedService {
  private confirmation = new BehaviorSubject<ZoomStatus>(ZoomStatus.hidden);
  private theme = new BehaviorSubject<ThemeEnum>(ThemeEnum.light);

  constructor() { }

  @Output() aClickedEvent = new EventEmitter<ThemeEnum>();

  public getTheme(): Observable<ThemeEnum> {
    return this.theme.asObservable()
  }

  public setTheme(): void {
    this.setThemeProperties();
  }

  public switchTheme(): void {
    this.theme.next((this.theme.value + 1) % 2);
    this.setThemeProperties();
  }

  public getConfirmation(): Observable<ZoomStatus> {
    return this.confirmation.asObservable();
  }

  public setConfirmation(status: ZoomStatus): void {
    this.confirmation.next(status);
  }

  private setThemeProperties(): void {
    const body: HTMLCollectionOf<any> = document.getElementsByTagName('body');
    const headers: HTMLCollectionOf<any> = document.getElementsByClassName('title');
    const headersTmp: HTMLCollectionOf<any> = document.getElementsByTagName('h1');

    if (this.theme.value === 1) {
      if (body.length) {
        body[0].style.background = 'var(--dark-theme)';
        body[0].style.color = '#FFF';
      }
      if (headers.length > 0) {
        headers[0].style.color = '#FFF';
      }
      for (let i = 0; i < headersTmp.length; i++) {
        headersTmp[i].style.color = '#FFF';
      }
    } else {
      if (body.length > 0) {
        body[0].style.background = 'var(--light-theme)';
        body[0].style.color = '#000';
      }
      if (headers.length > 0) {
        headers[0].style.color = '#000';
      }
      for (let i = 0; i < headersTmp.length; i++) {
        headersTmp[i].style.color = '#000';
      }
    }
  }
}
