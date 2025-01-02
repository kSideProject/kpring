import React, { useEffect, useState } from "react";
import FormField from "../molecules/FormField";
import Button from "../atoms/Button";
import CategorySelector from "../molecules/CategorySelector";
import ThemeSelector from "../molecules/ThemeSelector";
import Cookies from "js-cookie";
import useUserProfile from "@/hooks/user/useUserProfile";
import { createServer } from "@/api/server";
import { useLoginStore } from "@/store/useLoginStore";
import { CreateServerRequest } from "@/types/server";

const CreateServerForm: React.FC = () => {
  const userId = Cookies.get("userId");
  const { accessToken } = useLoginStore();
  const { userProfile } = useUserProfile();

  const [serverFormValues, setServerFormValues] = useState<CreateServerRequest>(
    {
      serverName: "",
      userId: userId || "",
      hostName: userProfile?.data.username || "",
      theme: "",
      categories: [],
    }
  );

  useEffect(() => {
    if (userProfile?.data.username && userId) {
      setServerFormValues((prev) => ({
        ...prev,
        userId: userId,
        hostName: userProfile.data.username,
      }));
    }
  }, [userProfile, userId]);

  const onChangeServerValues = (
    name: keyof CreateServerRequest,
    value: string | string[]
  ) => {
    setServerFormValues((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const onSubmitForm = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      const response = await createServer(accessToken, serverFormValues);
      return response;
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <form className="flex flex-col gap-5" onSubmit={onSubmitForm}>
      <FormField
        label="서버명"
        name="serverName"
        value={serverFormValues.serverName}
        onChange={(e) => onChangeServerValues("serverName", e.target.value)}
        message=""
      />
      <CategorySelector
        onChange={(categories) =>
          onChangeServerValues("categories", categories)
        }
        selectedCategories={serverFormValues.categories}
      />
      <ThemeSelector
        onChange={(theme) => {
          console.log("ThemeSelector onChange called with:", theme);
          onChangeServerValues("theme", theme);
        }}
        selectedTheme={serverFormValues.theme}
      />
      <Button color="">서버생성</Button>
    </form>
  );
};

export default CreateServerForm;
