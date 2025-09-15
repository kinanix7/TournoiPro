# Navigation Components Documentation

## Overview
The navigation system has been split into separate, modular components for better organization and maintainability.

## Components Structure

### 1. `NavigationComponent` (Main Router)
- **Location**: `/shared/navigation/navigation.component.ts`
- **Purpose**: Smart component that automatically selects the appropriate navigation based on user role
- **Usage**: `<app-navigation></app-navigation>`

### 2. `UserNavigationComponent` 
- **Location**: `/shared/user-navigation/user-navigation.component.ts`
- **Purpose**: Navigation for regular users and non-authenticated users
- **Features**:
  - Dashboard, Matches, Rankings links
  - User profile dropdown with logout
  - Login/Register links for non-authenticated users
- **Usage**: `<app-user-navigation></app-user-navigation>`

### 3. `AdminNavigationComponent`
- **Location**: `/shared/admin-navigation/admin-navigation.component.ts`
- **Purpose**: Specialized navigation for admin users
- **Features**:
  - Admin Dashboard link
  - "User Views" dropdown (Dashboard, Matches, Rankings)
  - "Management" dropdown (Players, Teams, Referees, Courts)
  - Match Management link
  - Admin profile dropdown with "Back to User View" option
- **Usage**: `<app-admin-navigation></app-admin-navigation>`

### 4. `NavigationWrapperComponent` (Optional)
- **Location**: `/shared/navigation-wrapper/navigation-wrapper.component.ts`
- **Purpose**: Force specific navigation types when needed
- **Usage**: 
  ```html
  <app-navigation-wrapper type="user"></app-navigation-wrapper>
  <app-navigation-wrapper type="admin"></app-navigation-wrapper>
  <app-navigation-wrapper type="auto"></app-navigation-wrapper> <!-- Default -->
  ```

## Navigation Logic

### Automatic Navigation Selection (Default)
The main `NavigationComponent` automatically shows:
- **Admin Navigation**: When user has ADMIN role
- **User Navigation**: When user has USER role or is not authenticated

### Navigation Features

#### User Navigation
- **Blue theme** (`bg-primary`)
- Shows basic user features
- Login/Register for unauthenticated users
- Profile dropdown with logout

#### Admin Navigation  
- **Dark theme** (`bg-dark`)
- Enhanced admin features organized in dropdowns
- "User Views" dropdown to access user perspective
- "Management" dropdown for admin operations
- "Back to User View" option to return to user interface

## Import Structure

### Individual Imports
```typescript
import { UserNavigationComponent } from '../shared/user-navigation/user-navigation.component';
import { AdminNavigationComponent } from '../shared/admin-navigation/admin-navigation.component';
import { NavigationComponent } from '../shared/navigation/navigation.component';
```

### Barrel Imports (Recommended)
```typescript
import { 
  NavigationComponent, 
  UserNavigationComponent, 
  AdminNavigationComponent,
  NavigationWrapperComponent,
  NavigationType 
} from '../shared';
```

## Benefits of This Structure

1. **Separation of Concerns**: Each navigation type has its own component
2. **Maintainability**: Easier to modify user or admin navigation independently
3. **Reusability**: Can use specific navigation types in different contexts
4. **Scalability**: Easy to add new navigation types (e.g., moderator navigation)
5. **Testing**: Each component can be tested in isolation

## Migration Notes

- Existing `<app-navigation>` usage continues to work without changes
- The main navigation component now acts as a smart router
- All existing functionality is preserved
- New admin navigation provides better UX for admin users

## Customization

To customize navigation for specific pages:
1. Use `NavigationWrapperComponent` with specific type
2. Create new navigation components following the same pattern
3. Extend the main `NavigationComponent` logic for new roles