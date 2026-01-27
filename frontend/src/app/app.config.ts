import {  ApplicationConfig, inject, provideAppInitializer, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient , withInterceptors} from '@angular/common/http';


import { routes } from './app.routes';
import { loggingInterceptor } from './core/interceptors/logging/logging-interceptor';
import { errorInterceptor } from './core/interceptors/error/error-interceptor';
import { apiResponseInterceptor } from './core/interceptors/api-response/api-response-interceptor';
import { authInterceptor } from './core/interceptors/auth/auth/auth-interceptor';
import { loaderInterceptor } from './core/interceptors/loader/loader-interceptor';
import { authRefreshInterceptor } from './core/interceptors/auth/refresh/refresh-interceptor';
import { AuthInitService } from './auth/services/auth-init.service';

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(
      withInterceptors([
       loggingInterceptor,       // (1) debug / logs
        loaderInterceptor,        // (2) UI loading
        apiResponseInterceptor,   // (3) unwrap API responses
       authInterceptor,          // (4) attach access token
      authRefreshInterceptor,   // (5) refresh on 401
        errorInterceptor          // (6) final error handling
      ])
    ),
    provideAppInitializer(() => {
      const authInit = inject(AuthInitService);
      return authInit.initialize();
    }),


    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    
  ]
};
