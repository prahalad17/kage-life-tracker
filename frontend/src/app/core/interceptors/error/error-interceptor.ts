import {
  HttpInterceptorFn,
  HttpErrorResponse,
  HttpResponse
} from '@angular/common/http';
import { catchError, map, throwError } from 'rxjs';

interface ApiErrorLike {
  success: boolean;
  message?: string;
}

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(

    map(event => {
      if (event instanceof HttpResponse) {
        const body = event.body;

        if (
          body &&
          typeof body === 'object' &&
          'success' in body &&
          (body as ApiErrorLike).success === false
        ) {
          const errorBody = body as ApiErrorLike;
          throw new Error(errorBody.message || 'Request failed');
        }
      }

      return event;
    }),

    catchError((error: HttpErrorResponse | Error) => {

      let message = 'Something went wrong';

      if (error instanceof HttpErrorResponse) {
        message = error.error?.message || error.message;
      }

      if (error instanceof Error) {
        message = error.message;
      }

      return throwError(() => new Error(message));
    })

  );
};
