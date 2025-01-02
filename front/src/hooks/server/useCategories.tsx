import { getCategories } from "@/api/server";
import { CategoriesType } from "@/types/server";
import { useEffect, useState } from "react";

const useServerCategories = () => {
  const [categories, setCategories] = useState<CategoriesType[] | null>(null);

  useEffect(() => {
    const fetchServerCategories = async () => {
      try {
        const response = await getCategories();
        if (response) {
          setCategories(response.data);
        }
      } catch (error) {
        throw new Error("");
      }
    };
    fetchServerCategories();
  }, []);
  return { categories };
};

export default useServerCategories;
