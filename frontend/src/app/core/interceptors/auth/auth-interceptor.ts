import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  // 1️⃣ Skip auth for login (and other public endpoints)
  if (req.url.includes('/login')) {
    return next(req);
  }

  // 2️⃣ Read token
  const token = localStorage.getItem('token');

  // 3️⃣ Attach token if available
  if (token) {
    const authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    return next(authReq);
  }

  // 4️⃣ No token → proceed normally
  return next(req);
};
