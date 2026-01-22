import { HttpInterceptorFn, HttpResponse } from '@angular/common/http';
import { map } from 'rxjs';

export const apiResponseInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(
    map(event => {

      // 1️⃣ Only handle actual HTTP responses
      if (!(event instanceof HttpResponse)) {
        return event;
      }

      // 2️⃣ Skip non-JSON responses (files, blobs, etc.)
      if (req.responseType !== 'json') {
        return event;
      }

      // 3️⃣ Skip explicitly marked requests
      if (req.headers.has('X-Skip-Api-Wrapper')) {
        return event;
      }

      const body = event.body;

      // 4️⃣ Body must be a non-null object (not array)
      if (
        typeof body !== 'object' ||
        body === null ||
        Array.isArray(body)
      ) {
        return event;
      }

      // 5️⃣ Minimal ApiResponse shape detection
      if (!('data' in body) || !('success' in body)) {
        return event;
      }

      // ✅ Safe to unwrap
      return event.clone({ body: body.data });
    })
  );
};