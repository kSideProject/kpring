import React from "react";
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
} from "@mui/material";
import { useNavigate } from "react-router";
import { refreshAccessToken } from "../../api/token";
import { useLoginStore } from "../../store/useLoginStore";

interface TokenRefreshAlertProps {
  open: boolean;
  onClose: () => void;
}

const TokenRefreshAlert: React.FC<TokenRefreshAlertProps> = ({
  open,
  onClose,
}) => {
  const { refreshToken, setTokens, clearTokens } = useLoginStore();
  const navigate = useNavigate();

  const handleExtend = async () => {
    const newTokens = await refreshAccessToken(refreshToken);
    if (newTokens) {
      setTokens(newTokens.accessToken, newTokens.refreshToken);
      onClose(); // 창 닫기
    } else {
      clearTokens();
      navigate("/login");
    }
  };

  const handleCancel = () => {
    clearTokens();
    navigate("/login");
  };

  return (
    <Dialog open={open} onClose={handleCancel}>
      <DialogTitle>로그인 연장</DialogTitle>
      <DialogContent>로그인이 곧 만료됩니다. 연장하시겠습니까?</DialogContent>
      <DialogActions>
        <Button onClick={handleExtend} color="primary">
          연장
        </Button>
        <Button onClick={handleCancel} color="secondary">
          취소
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default TokenRefreshAlert;
