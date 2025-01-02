import { useEffect, useState } from "react";
import { getTokenExpiration } from "../api/auth";

export function useTokenExpirationCheck(token: string, alertTime: number) {
  const [showAlert, setShowAlert] = useState(false);

  useEffect(() => {
    if (!token) return;

    const expirationTime = getTokenExpiration(token);
    const currentTime = Date.now();
    const timeLeft = expirationTime - currentTime;

    if (timeLeft > 0) {
      const timeout = setTimeout(() => {
        setShowAlert(true);
      }, timeLeft - alertTime);

      return () => clearTimeout(timeout);
    } else {
      setShowAlert(true); // 만료된 경우 즉시 알림
    }
  }, [token, alertTime]);

  return showAlert;
}
