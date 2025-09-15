import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserNavigationComponent } from '../user-navigation/user-navigation.component';
import { AdminNavigationComponent } from '../admin-navigation/admin-navigation.component';
import { NavigationComponent } from '../navigation/navigation.component';

export type NavigationType = 'user' | 'admin' | 'auto';

@Component({
  selector: 'app-navigation-wrapper',
  standalone: true,
  imports: [CommonModule, UserNavigationComponent, AdminNavigationComponent, NavigationComponent],
  templateUrl: './navigation-wrapper.component.html',
  styleUrls: ['./navigation-wrapper.component.css']
})
export class NavigationWrapperComponent {
  @Input() type: NavigationType = 'auto';
}