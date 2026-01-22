import { ApplicationConfig, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient , withInterceptors} from '@angular/common/http';


import { routes } from './app.routes';
import { loggingInterceptor } from './core/interceptors/logging/logging-interceptor';
import { errorInterceptor } from './core/interceptors/error/error-interceptor';
import { apiResponseInterceptor } from './core/interceptors/api-response/api-response-interceptor';
import { authInterceptor } from './core/interceptors/auth/auth-interceptor';
import { loaderInterceptor } from './core/interceptors/loader/loader-interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(
      withInterceptors([loggingInterceptor,
        errorInterceptor,
        apiResponseInterceptor,
        authInterceptor,
        loaderInterceptor
      ])
    ),
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    
  ]
};
