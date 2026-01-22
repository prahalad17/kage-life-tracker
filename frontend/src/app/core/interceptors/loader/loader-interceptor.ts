import { inject } from '@angular/core';
import { finalize } from 'rxjs';
import { LoaderService } from '../../services/loader.service';
import { HttpInterceptorFn } from '@angular/common/http';

export const loaderInterceptor: HttpInterceptorFn = (req, next) => {

  const loaderService = inject(LoaderService);
  
   // ✅ 1. Skip loader if explicitly requested
  if (req.headers.has('X-Skip-Loader')) {
    return next(req);
  }

  
   // 1️⃣ Show loader when request starts
  loaderService.show();
  return next(req).pipe(
    finalize(() => {
      // 2️⃣ Hide loader when request completes (success OR error)
      loaderService.hide();
    })
  );
};
